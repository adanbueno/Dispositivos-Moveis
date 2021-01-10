const mongoose = require('mongoose');
const AutoIncrement = require('mongoose-sequence')(mongoose);

const BookSchema = new mongoose.Schema({
  _id: Number,
  title: String,
  author: String,
  value: Number,
}, { _id: false });

BookSchema.plugin(AutoIncrement);


module.exports = mongoose.model('Book', BookSchema);
