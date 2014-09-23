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
	print "creating user_db_key"
	return ndb.Key('users', user_db)

def readFromDB():
	print "Read from the DB?"

class UserEntry(ndb.Model):
	"""Model Object for individual entry"""

	name = ndb.StringProperty()
	identifier = ndb.StringProperty(indexed=False)
	profileurl = ndb.StringProperty(indexed=False)
	headline = ndb.StringProperty(indexed=False)
	picurl = ndb.StringProperty(indexed=False)

class BaseHandler(webapp2.RequestHandler):

	def handle_exception(self, exception, debug):


		self.response.write('An error occurred.')
		# If the exception is a HTTPException, use its error code.
		# Otherwise use a generic 500 error code.
		if isinstance(exception, webapp2.HTTPException):
			self.response.set_status(exception.code)
		else:
			self.response.set_status(500)


class BreezeMain(BaseHandler):
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

# Register class - lets have the post methods insert into DB
# 	and the GET method read all the entries out of it.
class Register(BaseHandler):
	def get(self):
		#Read out of the datastore

		#entry_name = self.request.get('entry_name', USER_DB)
		entry = UserEntry(parent=user_db_key('user_db'))
		entry.name = 'George Martin'
		entry.identifier = 'someid12345'
		entry.profileurl = 'http://someurl.com/picture.jpg'
		entry.put()

		query = UserEntry.query(ancestor=user_db_key(USER_DB))

		results = query.fetch(10)

		for result in results:
			if result.name:
				self.response.write('Result Name: ' + result.name + ' |\t')
			if result.identifier:
				self.response.write('Result Identifier: ' + result.identifier + ' |\t')
			if result.profileurl:
				self.response.write('Result URL: ' + result.profileurl + ' |\t')

			self.response.write('<p>')

	def post(self):
		#Insert some stuff into the datastore
		self.response.write('Register POST')

		entry_name = self.request.get('entry_name', USER_DB)

		query = UserEntry(parent=user_db_key(entry_name))

		query.name = self.request.get('name')
		query.put()
		self.response.write('Saved to DB?')


class GetNearby(webapp2.RequestHandler):
	def get(self):
		self.response.write('GetNearby GET')

	def post(self):
		self.response.write('GetNearby POST')

application = webapp2.WSGIApplication([
	('/', BreezeMain),
	('/register', Register),
	('/nearby', GetNearby)], debug=True)