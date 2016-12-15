
Project is developped in Intellj using Postgres sdl database and Spring Boot.

This Ws supports 2 kinds of data json and HTML representation  .

For Html representation it's like a normal website .

For Json representation here is some calls that can be usefull .

Ressources (models )  are : 

 User : 
 
 
1.	Integer Id 
2.	String email
3.	String password
4.	String first name
5.	String last name
6.	String biography
7.	Collection of comments 


	Group : 
1.	Integer id 
2.	String name
3.	String description
4.	String admin email adresse
5.	Collection of users
6.	Collection of comments


	Role : 
	1.    Integer id
	2.    String description
	3.    String name 

	Comment : 
1.    Integer id 
2.    String  Owner firstName
3.    String Owner lastName
4.    String comment


For json Representation :

	If you type : json/user/edit/fullName/firstName/lastName :
	This get methode will make the user changes his/her full name.

	If you type : json/user/edit/biography/thebiography :
	This get method will make the user changes his/her biography.

	If you type : json/user/delete :
	This delete method makes the user deletes his/her account.

	If you type : json/user/new/group :
	This post method will make the user creates new group.

	If you type : json/user/group/edit/name/description:
	This post method will make the user edit description of a group he owns.

	If you type : json/user/groups :
	This post method will make the user view a list of groups and membership count.

	If you type : json/user/group/join/name:
	This get method will make the user joins group.

	If you type : json/user/group/view/name:
	This get method will make the user views members of the group he/she owns or has joined.

	If you type : json/user/group/leave/name:
	This get method will make the user leaves the group.

	If you type : json/users/firstName/lastName :
	This get method will make the user check the profile of other users.

	If you type : json/comment/comment/groupName/thecomment :
	This get method will make the user user comments on the dashboard of the group.
