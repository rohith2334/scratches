// const mongoose = require('mongoose');
const models = require("./model.js");
const faker = require("faker");
const mongoose = require("mongoose");
const fs = require("fs");
const path = require("path");
const { get } = require("http");

function getOrder() {
  let order = [];
  for (let [name, model] of Object.entries(models)) {
    if (model && model.schema && model.schema.paths) {
      for (let path of Object.values(model.schema.paths)) {
        if (
          path.options &&
          path.options.ref &&
          !order.includes(models[path.options.ref])
        ) {
          order.push(models[path.options.ref]);
        }
      }
    }
    if (!order.includes(model)) {
      order.push(model);
    }
  }

  return order;
}

function readFileAsBinary(filePath) {
  
  return fs.readFileSync(filePath);
}

async function uploadDocument(folderPath, Document) {
  const files = fs.readdirSync(folderPath);
  for (let file of files) {
    const document = new Document();
    const filePath = path.join(folderPath, file);
    const fileData = readFileAsBinary(filePath);
    document.filename = file;
    document.length = fileData.length;
    document.chunkSize = 255;
    document.content = fileData;
    document.metadata = {
      fileType: path.extname(filePath),
    };
    await document.save();
  }
}

async function getImage(value) {
  var folderPath = "";
  if (value.options.isAvatar) {
    folderPath = "./documents/photos/avatars";
  } else {
    folderPath = "./documents/photos/projectSpecific";
  }
  const files = fs.readdirSync(folderPath);
  const randomIndex = faker.datatype.number({
    min: 0,
    max: files.length - 1,
  });
  const filePath = path.join(folderPath, files[randomIndex]);
  const fileData = readFileAsBinary(filePath);
  return fileData;
}

async function handleArrayObjectId(value) {
  if (value.options.type[0].ref) {
    const refModel = mongoose.model(value.options.type[0].ref);
    const refDocuments = await refModel.find();
    if (refDocuments.length > 0) {
      const randomIndex = faker.datatype.number({
        min: 0,
        max: refDocuments.length - 1,
      });

      return refDocuments[randomIndex]._id;
    }
  }
}

async function handleObjectId(value) {
  if (value.options.ref) {
    // If the field is a reference, find a random document of that type
    const refModel = mongoose.model(value.options.ref);
    const refDocuments = await refModel.find();
    if (refDocuments.length > 0) {
      const randomIndex = faker.datatype.number({
        min: 0,
        max: refDocuments.length - 1,
      });
      return refDocuments[randomIndex]._id;
    }
  } else {
    // For non-reference ObjectId fields, generate a fake ObjectId
    return new mongoose.Types.ObjectId();
  }
}

async function getData(instance) {
  switch (instance) {
    case "String":
      return faker.random.word();
    case "Number":
      return faker.datatype.number();
    case "Date":
      return faker.date.recent();
    case "Boolean":
      return faker.datatype.boolean();
    default:
      return null;
  }
}

async function generateFakeData(schema) {
  const fakeData = {};
  for (const [key, value] of Object.entries(schema.paths)) {
    if (key !== "__v") {
      // console.log(value);
      switch (value.instance) {
        case "Buffer":
          if (value.options.isImage) {
            fakeData[key] = await getImage(value);
          }
          break;
        case "ObjectId":
          fakeData[key] = await handleObjectId(value);
          break;
        case "Array":
          // handle array of objectIds refs
          if (Array.isArray(value.options.type) && value.options.type[0].ref) {
            fakeData[key] = await Promise.all(
              Array.from(
                { length: faker.datatype.number({ min: 1, max: 5 }) },
                async () => await handleArrayObjectId(value)
              )
            );
          }
          // handle images array
          else if (
            Array.isArray(value.options.type) &&
            value.options.type[0].isImage
          ) {
            fakeData[key] = await Promise.all(
              Array.from(
                { length: faker.datatype.number({ min: 1, max: 5 }) },
                async () => await getImage(value)
              )
            );
          }
          // handle normal array
          else {
            fakeData[key] = await Promise.all(
              Array.from(
                { length: faker.datatype.number({ min: 1, max: 5 }) },
                async () => await getData(value.instance)
              )
            );
          }

          break;
        default:
          fakeData[key] = await getData(value.instance);
          break;
      }
    }
  }
  return fakeData;
}

recordsCount = {
  Role: 3,
  User: 3,
  Profile: 3,
  Property: 5,
  Application: 20,
  Review: 22,
  Session: 4,
  Document: 4,
  Notification: 25,
};

mongoose
  .connect("mongodb://localhost:27017/ttt", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(async () => {
    console.log("Connected to MongoDB");

    async function populateDatabase() {
      const modelsOrder = getOrder();
      // Populate database with fake data for each schema
      for (let model of modelsOrder) {
        // print schema name
        if (model.modelName == "Document") {
          console.log("Documents schema found");
          const document = await uploadDocument(
            "./documents/files",
            models.Document
          );
        } else {
          for (let i = 0; i < recordsCount[model.modelName]; i++) {
            const fakeData = await generateFakeData(model.schema);
            await model.create(fakeData);
          }
        }
      }
    }
    populateDatabase()
      .then(() => console.log("Database populated"))
      .catch((err) => console.error("Error populating database:", err))
      .finally(() => {
        mongoose.connection.close();
      });
  });
