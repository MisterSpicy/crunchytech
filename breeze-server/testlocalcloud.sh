curl -X POST --data "name=bob&ident=12345&profileurl=httpcolonslash4&headline=I%20AM%20A%20BANANA&picurl=knife.jpg" http://localhost:5000/register
curl -X POST --data "name=tom&ident=12346&profileurl=httpcolonslash2&headline=I%20AM%20A%20BANANA&picurl=gun.jpg" http://localhost:5000/register
curl -X POST --data "name=larry&ident=12347&profileurl=httpcolonslash3&headline=I%20AM%20A%20BANANA&picurl=needle.jpg" http://localhost:5000/register

curl -X GET http://localhost:5000/getnearby

