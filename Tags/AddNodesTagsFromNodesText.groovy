// @ExecutionModes({on_selected_node="/main_menu/edit/tags"})

// For each node in the map,
// each #xxxxxx word in a node text, details or note is added
// as a new hashtag attribute in this node.
// Each hashtag attribute remains unique.
// Author: lilive
// Licence: WTFPL2

c.findAll().each{
    node ->
    def tags = ( node.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
    if( node.details ) tags += ( node.details.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
    if( node.note ) tags += ( node.note.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
    def stored = node.attributes.getAll("hashtag")
    tags.each{ if(!(it in stored)) node.attributes.add( "hashtag", it ) }
}
