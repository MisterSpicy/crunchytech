from google.appengine.api import users
from google.appengine.ext import ndb


import webapp2
import cgi
import urllib
import json
from pprint import pprint

MAIN_PAGE_HTML = """\
<html>
	<body>
		<h1> Breeze Server </h1>
	</body>
</html>

"""

def user_db_key(user_db='person'):
	"""Create a key for the datastore to use"""
	print "creating user_db_key"
	return ndb.Key('users', user_db)

def readFromDB():
	print "Read from the DB?"

def nearbyResponse(name, ident, profileurl, headline, picurl):
	print "Creating JSON Response"
	
	obj = {
		'name' : name,
		'ident' : ident,
		'profileurl' : profileurl,
		'headline' : headline,
		'picurl' : picurl
	}

	pprint(obj)

	return obj


def queryNearby():
	print "queryNearby"
	query = UserEntry.query(ancestor=user_db_key())

	#Result is going to be a list of UserEntry objects
	results = query.fetch(10)

	print "getting Response"
	response = dict((r.ident, nearbyResponse(r.name, r.ident, r.profileurl, r.headline, r.picurl)) for r in results)
	pprint(response)
	#json_string = json.dumps(response)

	#print "Printing JSON String\n"
	#pprint(json_string)

class UserEntry(ndb.Model):
	"""Model Object for individual entry"""

	name = ndb.StringProperty(indexed=False)
	ident = ndb.StringProperty(indexed=True)
	profileurl = ndb.StringProperty(indexed=False)
	headline = ndb.StringProperty(indexed=False)
	picurl = ndb.StringProperty(indexed=False)

	@classmethod
	def query_book(cls, ancestor_key):
		return cls.query(ancestor=ancestor_key)

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
		entry = UserEntry(parent=user_db_key())
		entry.name = 'George Martin'
		entry.identifier = 'someid12345'
		entry.profileurl = 'http://someurl.com/picture.jpg'
		entry.put()

		anc_key = UserEntry.query(ancestor=user_db_key())
		results = anc_key.fetch(10)

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

		name = self.request.get('name')
		ident = self.request.get('ident')
		profileurl = self.request.get('profileurl')
		headline = self.request.get('headline')
		picurl = self.request.get('picurl')

		print "(name, ident, profileurl, headline, picurl) : " + name + '\t' + ident + '\t' + profileurl + '\t' + headline + '\t' + picurl 

		new_user = UserEntry(parent=user_db_key())

		new_user.name = self.request.get('name')
		new_user.ident = ident
		new_user.profileurl = profileurl
		new_user.headline = headline
		new_user.picurl = picurl

		new_user.put()


class GetNearby(webapp2.RequestHandler):
	def get(self):
		self.response.write('GetNearby GET\n')
		queryNearby()

	def post(self):
		self.response.write('GetNearby POST\n')

application = webapp2.WSGIApplication([
	('/', BreezeMain),
	('/register', Register),
	('/nearby', GetNearby)], debug=True)