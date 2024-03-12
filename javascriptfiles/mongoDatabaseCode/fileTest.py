from pymongo import MongoClient
import gridfs
import os

# Create a client connection to your MongoDB instance
client = MongoClient('mongodb://localhost:27017/')

# Connect to your 'ttt' database
db = client['ttt']  

# Connect to your 'documents' collection
collection = db['documents']


# fetech all the documents from the collection and download file which is stored as a binary data in content field with extenstion in metadata[filetype]
for document in collection.find():
    print(document['filename'])
    with open(document['filename'], 'wb') as f:
        f.write(document['content'])
    print("File Downloaded")
    # print("File Type: ", document['metadata']['filetype'])
    print("File Size: ", os.path.getsize(document['filename'])/1024, "KB")
    print("File Downloaded Successfully")
    print("-------------------------------------------------")

# download all images from users collection which is stored as a binary data in profile will not have anything just profile with base64 string
# collection = db['users']
# for document in collection.find():
#     print(document['username'])
#     with open(document['username'] + ".png", 'wb') as f:
#         f.write(document['profile'])
#     print("File Downloaded")
    # print("File Size: ", os.path.getsize(document['username'] + ".jpg")/1024, "KB")
    # print("File Downloaded Successfully")
    # print("-------------------------------------------------")
    
# Don't forget to close the connection
client.close()