import mongoose from 'mongoose';

const TransferSchema = mongoose.Schema({
  id_sender: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
  },
  id_recipient: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
  },
  value: {
    type: Number,
  },
  created_at: {
    type: Date,
    required: true,
    default: Date.now,
  },
});

export default mongoose.model('Transfer', TransferSchema);
