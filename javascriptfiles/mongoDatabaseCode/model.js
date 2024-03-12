const mongoose = require("mongoose");

// Define schema for Documents
const 
documentSchema = new mongoose.Schema(
  {
    filename: { type: String },
    length: { type: Number },
    chunkSize: { type: Number },
    uploadDate: { type: Date, default: Date.now },
    content: { type: Buffer },
    metadata: { type: Object },
  },
  { versionKey: false }
);

// Define schema for Roles
const roleSchema = new mongoose.Schema(
  {
    name: { type: String, required: true },
  },
  { versionKey: false }
);

// Define schema for Users
const userSchema = new mongoose.Schema(
  {
    username: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    email: { type: String },
    roles: [{ type: mongoose.Schema.Types.ObjectId, ref: "Role" }],
  },
  { versionKey: false }
);

// Define schema for Profiles
const profileSchema = new mongoose.Schema(
  {
    user: { type: mongoose.Schema.Types.ObjectId, ref: "User" },
    fullName: { type: String },
    phoneNumber: { type: String },
    address: { type: String },
    lastLogin: { type: Date },
    active: { type: Boolean, default: true },
    profile: { type: Buffer, contentType: String, filename: String , isImage: Boolean, isAvatar:Boolean},
    saved: [{ type: mongoose.Schema.Types.ObjectId, ref: "Property" }],
  },
  { versionKey: false }
);

// Define schema for Properties
const propertySchema = new mongoose.Schema(
  {
    owner: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "User",
      required: true,
    },
    name: { type: String, required: true },
    description: { type: String },
    type: { type: String },
    location: { type: String },
    price: { type: Number },
    bedrooms: { type: Number },
    bathrooms: { type: Number },
    amenities: { type: [String] },
    availability: { type: Boolean },
    verificationStatus: { type: String },
    privacySetting: { type: Boolean },
    communicationHistory: { type: Object },
    rentalAgreement: { type: mongoose.Schema.Types.ObjectId, ref: "Document" }, // Rental agreement document reference
    additionalDocuments: [
      { type: mongoose.Schema.Types.ObjectId, ref: "Document" },
    ],
    images: [{ type: Buffer, contentType: String, filename: String , isImage: Boolean}],
    avatar: { type: Buffer, contentType: String, filename: String , isImage: Boolean,isAvatar:Boolean},

    address: { type: String },
    size: { type: Number },
    yearBuilt: { type: Number },
    floorPlan: { type: mongoose.Schema.Types.ObjectId, ref: "Document" },
    createdBy: { type: mongoose.Schema.Types.ObjectId, ref: "User" },
    createdAt: { type: Date, default: Date.now },
    updatedAt: { type: Date, default: Date.now },
    rentalTerms: { type: String },
    utilitiesIncluded: { type: Boolean },
    petsAllowed: { type: Boolean },
    leaseStartDate: { type: Date },
    leaseEndDate: { type: Date },
    applicationDeadline: { type: Date },
    openHouseDates: [{ type: Date }],
    virtualTourLink: { type: String },
    propertyManager: { type: mongoose.Schema.Types.ObjectId, ref: "User" },
    reviews: [{ type: mongoose.Schema.Types.ObjectId, ref: "Review" }],
    ratingAverage: { type: Number },
    isActive: { type: Boolean, default: true },
  },
  { versionKey: false }
);

// Define schema for Applications
const applicationSchema = new mongoose.Schema(
  {
    user: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    property: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Property",
      required: true,
    },
    status: { type: String },
    message: { type: String },
    createdAt: { type: Date, default: Date.now },
    updatedAt: { type: Date, default: Date.now },
    moveInDate: { type: Date },
    emergencyContact: { type: String },
    employmentDetails: { type: String },
    creditReport: { type: mongoose.Schema.Types.ObjectId, ref: "Document" },
  },
  { versionKey: false }
);

// Define schema for Reviews
const reviewSchema = new mongoose.Schema(
  {
    user: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    property: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Property",
      required: true,
    },
    rating: { type: Number },
    comment: { type: String },
    createdAt: { type: Date, default: Date.now },
    updatedAt: { type: Date, default: Date.now },
    isVerified: { type: Boolean, default: false },
    reported: { type: Boolean, default: false },
    reportedBy: [{ type: mongoose.Schema.Types.ObjectId, ref: "User" }],
  },
  { versionKey: false }
);

// Define schema for Notifications
const notificationSchema = new mongoose.Schema(
  {
    user: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    message: { type: String },
    type: { type: String },
    createdAt: { type: Date, default: Date.now },
    isRead: { type: Boolean, default: false },
    link: { type: String },
  },
  { versionKey: false }
);

// Define schema for Sessions
const sessionSchema = new mongoose.Schema(
  {
    user: { type: mongoose.Schema.Types.ObjectId, ref: "User" },
    sessionId: { type: String },
    loginTime: { type: Date, default: Date.now },
    logoutTime: { type: Date },
  },
  { versionKey: false }
);


// Create models from the schemas
const Role = mongoose.model("Role", roleSchema);
const Profile = mongoose.model("Profile", profileSchema);
const User = mongoose.model("User", userSchema);
const Property = mongoose.model("Property", propertySchema);
const Application = mongoose.model("Application", applicationSchema);
const Review = mongoose.model("Review", reviewSchema);
const Session = mongoose.model("Session", sessionSchema);
const Document = mongoose.model("Document", documentSchema);
const Notification = mongoose.model("Notification", notificationSchema);



// Export the models
module.exports = {
  Role,
  Profile,
  User,
  Property,
  Application,
  Review,
  Notification,
  Session,
  Document,
};
