import mongoose from 'mongoose';
import Partners from './Partners';

const AdminTransferSchema = mongoose.Schema({
  id_sender: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'UserAdmin',
    required: true,
  },
  id_recipient: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Store',
    required: true,
  },
  value: {
    type: Number,
    required: true,
  },
  created_at: {
    type: Date,
    required: true,
    default: Date.now,
  },
  update_at: {
    type: Date,
    required: true,
    default: Date.now,
  },
});

AdminTransferSchema.pre('save', async function save(next) {
  const transfer = this;
  const partners = await Partners.findById(transfer.id_recipient);
  const balance = partners.balance + transfer.value;
  await partners.updateOne({ balance });
  next();
});

export default mongoose.model('AdminTransfer', AdminTransferSchema);
