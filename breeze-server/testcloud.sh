curl -X POST --data "name=bob&ident=12345&profileurl=http%3A%2F%2Fwww.linkedout.com%2Fsomeprofile1.jpg&headline=I%20AM%20A%20BANANA&picurl=knife.jpg" http://107.170.245.92:5000/register
curl -X POST --data "name=tom&ident=12346&profileurl=http%3A%2F%2Fwww.linkedout.com%2Fsomeprofile2.jpg&headline=I%20AM%20A%20BANANA&picurl=needle.jpg" http://107.170.245.92:5000/register
curl -X POST --data "name=larry&ident=12347&profileurl=http%3A%2F%2Fwww.linkedout.com%2Fsomeprofile3.jpg&headline=I%20AM%20A%20BANANA&picurl=gun.jpg" http://107.170.245.92:5000/register

curl -X GET http://107.170.245.92:5000/getnearby

