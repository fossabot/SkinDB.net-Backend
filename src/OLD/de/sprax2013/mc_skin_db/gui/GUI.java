package OLD.de.sprax2013.mc_skin_db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import OLD.de.sprax2013.mc_skin_db.skin.Skin;
import OLD.de.sprax2013.mc_skin_db.skin.Tag;
import OLD.de.sprax2013.mc_skin_db.skin.TagManager;
import OLD.de.sprax2013.mc_skin_db.util.DownloadUtils;
import de.sprax2013.skindb.back_end.utils.RemoteDatabaseUtils;

public class GUI {
	private static JFrame frmMcSkinDB;
	private static JLabel lblSkinPreview;

	static int currSkinIndex = -1;
	static List<Skin> skins;

	private static HashMap<String, BufferedImage> localSkinCache = new HashMap<>();
	private static JTextField txtSkinURL;
	private static JTextArea txtAreaTag;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void init() {
		skins = RemoteDatabaseUtils.getSkins().stream().filter(s -> {
			return !s.hasDuplicate() && s.getTags().isEmpty();
		}).collect(Collectors.toList());

		Collections.shuffle(skins);

		initGUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initGUI() {
		frmMcSkinDB = new JFrame();
		frmMcSkinDB.setTitle("McSkin-DB (Dev GUI)");
		frmMcSkinDB.setResizable(false);
		frmMcSkinDB.setBounds(100, 100, 615, 485);
		frmMcSkinDB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMcSkinDB.getContentPane().setLayout(null);

		lblSkinPreview = new JLabel();
		lblSkinPreview.setBounds(10, 33, 237, 384);
		frmMcSkinDB.getContentPane().add(lblSkinPreview);

		txtSkinURL = new JTextField();
		txtSkinURL.setEditable(false);
		txtSkinURL.setBounds(12, 429, 235, 20);
		frmMcSkinDB.getContentPane().add(txtSkinURL);
		txtSkinURL.setColumns(10);

		txtAreaTag = new JTextArea();
		txtAreaTag.setEditable(false);
		txtAreaTag.setBounds(275, 33, 322, 346);
		frmMcSkinDB.getContentPane().add(txtAreaTag);

		JTextField txtTag = new JTextField();
		txtTag.setBounds(275, 391, 206, 20);
		frmMcSkinDB.getContentPane().add(txtTag);
		txtTag.setColumns(10);

		JButton btnToggleTag = new JButton("Toggle");
		btnToggleTag.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtTag.getText().trim().isEmpty() && skins.get(currSkinIndex) != null) {
					Tag tag = TagManager.getTagOrCreate(txtTag.getText().trim());

					if (skins.get(currSkinIndex).hasTag(tag.getID())) {
						skins.get(currSkinIndex).removeTag(tag.getID());
					} else {
						skins.get(currSkinIndex).addTag(tag.getID());
					}

					reloadSkin();

					txtTag.setText("");
				}
			}
		});
		btnToggleTag.setBounds(499, 388, 98, 26);
		frmMcSkinDB.getContentPane().add(btnToggleTag);

		JLabel lblTag = new JLabel("Tag: ");
		lblTag.setLabelFor(txtTag);
		lblTag.setBounds(244, 393, 28, 16);
		frmMcSkinDB.getContentPane().add(lblTag);

		JButton btnPrev = new JButton("<<");
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prevSkin();
			}
		});
		btnPrev.setBounds(275, 426, 98, 26);
		frmMcSkinDB.getContentPane().add(btnPrev);

		JButton btnNext = new JButton(">>");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextSkin();
			}
		});
		btnNext.setBounds(385, 426, 98, 26);
		frmMcSkinDB.getContentPane().add(btnNext);

		JLabel lblAktuelleTags = new JLabel("Aktuelle Tags");
		lblAktuelleTags.setLabelFor(txtAreaTag);
		lblAktuelleTags.setBounds(275, 12, 77, 16);
		frmMcSkinDB.getContentPane().add(lblAktuelleTags);

		nextSkin();

		frmMcSkinDB.setVisible(true);
	}

	static void nextSkin() {
		currSkinIndex++;

		reloadSkin();
	}

	static void prevSkin() {
		currSkinIndex--;

		if (currSkinIndex < 0) {
			currSkinIndex = 0;
		}

		reloadSkin();
	}

	static void reloadSkin() {
		if (skins.size() > 0) {
			if (skins.size() >= currSkinIndex) {
				lblSkinPreview.setIcon(new ImageIcon(getSkinFile(skins.get(currSkinIndex).getURL())));
				txtSkinURL.setText(skins.get(currSkinIndex).getURL());

				String tagListStr = null;

				for (int tagID : skins.get(currSkinIndex).getTags()) {
					if (tagListStr != null) {
						tagListStr += ", " + TagManager.getTag(tagID).getIdentifier();
					} else {
						tagListStr = TagManager.getTag(tagID).getIdentifier();
					}
				}

				txtAreaTag.setText(tagListStr);
			} else if (currSkinIndex != 0) {
				prevSkin();
			}
		} else {
			lblSkinPreview.setIcon(new ImageIcon(getSkinFile(null)));
			txtSkinURL.setText("Kein Skin");

			txtAreaTag.setText("");
		}
	}

	private static BufferedImage getSkinFile(String skinURL) {
		if (!localSkinCache.containsKey(skinURL)) {
			try {
				String apiURL = "https://changelog.cherrybread.net/mc-3d/3d.php";

				if (skinURL != null) {
					apiURL += "?user=" + URLEncoder.encode(skinURL, "UTF-8");
				}

				localSkinCache.put(skinURL, ImageIO.read(DownloadUtils.getInputStream(apiURL)));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return localSkinCache.get(skinURL);
	}
}