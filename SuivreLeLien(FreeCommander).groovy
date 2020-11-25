// @ExecutionModes({on_single_node="/main_menu/navigate/links"})

// Suit le lien comme Menu > Navigation > Lien > Suivre le lien
// mais ouvre les dossiers dans freecommander
// Author: lilive
// Licence: WTFPL2

import java.awt.Desktop

// Il faut d�terminer si le noeud contient un lien vers un dossier.
// C'est facile: node.link.file.isDirectory()
// Mais quand le lien du noeud est un lien vers un autre noeud de la carte,
// node.link.file correspond au dossier contenant la carte.
// J'ai donc trouv� cette solution pour contourner le probl�me,
// sachant que node.link.uri est dans ce cas l'ID du noeud :
def isFile = false
if( node.link.uri && node.link.file ){
	def s1 = node.link.uri.toString().replaceAll("(%20)|[^\\w]", "" )
	def s2 = node.link.file.absolutePath.replaceAll("(%20)|[^\\w]", "" )
	isFile = s1.contains(s2) || s2.contains(s1)
}

if( isFile )
{
	if( node.link.file.isDirectory() ){
		def filename = node.link.file.absolutePath
		if ( filename ){
			filename = '"' + filename + '"'
			command = '"C:\\Program Files (x86)\\FreeCommander XE\\FreeCommander.exe" -T'
			command = command + ' ' + filename
			command.execute()
			return
		}
	}
}

// Si on n'a pas ouvert un dossier avec FreeCommander, ouvrir le lien
// comme le fait freeplane par d�faut
menuUtils.executeMenuItems(['FollowLinkAction'])
