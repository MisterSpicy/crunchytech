from google.appengine.api import users

import webapp2

MAIN_PAGE_HTML = """\
<html>
	<body>
		<h1> Breeze Server </h1>
	</body>
</html>

"""

class BreezeModel():
	"""
	Database Model
	"""

	#Name
	#Identifier
	#ProfileURL
	#picURL
	#Headline


class BreezeMain(webapp2.RequestHandler):
	def get(self):
		self.response.headers['Content-Type'] = 'text/plain'
		self.response.write(MAIN_PAGE_HTML)

	
	def post(self):
		self.response.headers['Content-Type'] = 'text/plain'
		self.response.write('POST World')


class Register(webapp2.RequestHandler):
	def get(self):


	def post(self):


class GetNearby(webapp2.RequestHandler):
	def get(self):
		self.response.write(MAIN_PAGE_HTML)
		
	def post(self):


application = webapp2.WSGIApplication([
	('/', BreezeMain),
	('/register', Register)], debug=True)