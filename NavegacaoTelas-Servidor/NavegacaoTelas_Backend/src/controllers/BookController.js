const Book = require('../models/Book');

module.exports = {
  async show(req, res) {
    const books = await Book.find();

    return res.json(books);
  },

  async store(req, res) {
    const { title, author, value } = req.body;

    let book = await Book.findOne({ title });

    if (!book) {
      book = await Book.create({
        title,
        author,
        value,
      });
    }

    return res.json(book);
  },

  async update(req, res) {
    const { id } = req.params;
    const { title, author, value } = req.body;

    const book = await Book.findOne({ _id: id });

    if(!book) {
      return res.status(404).json({ message: 'Livro não encontrado' });
    }

    Object.assign(book, { title, author, value });

    await book.save();

    return res.json(book);
  },

  async destroy(req, res) {
    const { id } = req.params;

    const book = await Book.findOne({ _id: id });

    await book.remove();

    //await Book.findOneAndDelete({ id });

    return res.status(202).json();
  },
};
