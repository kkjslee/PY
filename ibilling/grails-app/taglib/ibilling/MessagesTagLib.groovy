package ibilling


class MessagesTagLib {
	
	static namespace = "jB"
	
	def renderErrorMessages = { attrs, body ->
		out << render(template:"/errorTag")
	}

}
