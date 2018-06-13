# File storage server
This is a project i made when i questioned myself how applications like Facebook and Instagram store all the images.
Because they can't store all the images on 1 server, so how do they handle all the files?. My solution works by running this server application
on all the servers that is going to store the files. Then i have a database that contains information about each file and where they can be
retrieved. 

## The protocol

You need to get the path or ID, of the file
by connecting to the database in your own client.

### GET

To get a file by ID you can use the following example:


```
CLIENT: GET 1
SERVER: (bytes)
```

To get a file by PATH you can use:

```
CLIENT: GET C:\Users\Server\Documents\storage\0.dat
SERVER: (bytes)
```

### UPLOAD

To upload a password secured file to the server you can use:

```
CLIENT: UPLOAD <password>
CLIENT: (bytes)
SERVER: OK File is now uploaded
```

To upload a file to the server without a password you can use:

```
CLIENT: UPLOAD
CLIENT: (bytes)
SERVER: OK File is now uploaded
```
