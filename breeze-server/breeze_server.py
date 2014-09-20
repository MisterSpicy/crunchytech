from google.appengine.api import users
from google.appengine.ext import ndb


import webapp2
import cgi
import urllib

MAIN_PAGE_HTML = """\
<html>
	<body>
		<h1> Breeze Server </h1>
	</body>
</html>

"""

USER_DB = 'user_db'

def user_db_key(user_db=USER_DB):
	"""Create a key for the datastore to use"""
	return ndb.key('users', user_db)

def readFromDB():
	print "Read from the DB?"

class UserEntry(ndb.Model):
	"""Model Object for individual entry"""

	name = ndb.StringProperty()
	identifier = ndb.StringProperty()
	#profileurl = ndb.StringProperty()
	#headline = ndb.StringProperty()
	#picurl = ndb.StringProperty()


class BreezeMain(webapp2.RequestHandler):
	def get(self):
		self.response.headers['Content-Type'] = 'text/plain'
		self.response.write(MAIN_PAGE_HTML)

	
	def post(self):
		self.response.headers['Content-Type'] = 'text/plain'
		self.response.write('POST World')
		name = self.request.get('name')
		identifier = self.request.get('id')

		# Creates a new model object for the datastore
		user_page = UserEntry(parent=user_db_key())

		user_page.name = name
		user_page.identifier = identifier

		#Store the user db values to the database
		user_page.put()
		# self.redirect?  Naw




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