import * as Yup from 'yup';
import Transfer from '../models/Transfer';
import User from '../models/User';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      id_recipient: Yup.string().required(),
      value: Yup.number().positive().required(),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { id: id_sender } = req.user_id;
    const { id_recipient, value } = req.body;

    const userSend = await User.findById(id_sender);
    const userRecipient = await User.findById(id_recipient);

    if (!userSend) {
      return res.status(400).json({ error: 'Invalid user submission' });
    }

    if (!userRecipient) {
      return res.status(400).json({ error: 'Invalid user submission' });
    }

    if (userSend.balance - value < 0) {
      return res.status(400).json({ error: 'Insufficient funds' });
    }

    let balance = userSend.balance - value;
    await userSend.updateOne({ balance });

    balance = userRecipient.balance + value;
    await userRecipient.updateOne({ balance });

    Object.assign(req.body, { id_sender });
    let transfer = await await Transfer.create(req.body);

    transfer = await transfer
      .populate({ path: 'id_recipient', select: 'name' })
      .execPopulate();

    const result = {};
    const { _id, created_at } = transfer;
    const { name } = transfer.id_recipient;
    Object.assign(result, { _id, name, value, created_at });

    return res.json(result);
  },

  async index(req, res) {
    const { id } = req.user_id;

    const transferPositive = await Transfer.find({ id_recipient: id })
      .populate({ path: 'id_sender', select: 'name' })
      .exec();

    const resultTransferPositive = transferPositive.map((transfer) => {
      const result = {};
      const { _id, value, created_at } = transfer;
      const { name } = transfer.id_sender;
      Object.assign(result, { _id, name, value, created_at });
      return result;
    });

    const transferNegative = await Transfer.find({ id_sender: id })
      .populate({ path: 'id_recipient', select: 'name' })
      .exec();

    const resultTransferNegative = transferNegative.map((transfer) => {
      const result = {};
      const { _id, value, created_at } = transfer;
      const { name } = transfer.id_recipient;
      Object.assign(result, { _id, name, value: -value, created_at });
      return result;
    });

    const transfer = [...resultTransferPositive, ...resultTransferNegative];

    transfer.sort((extA, extB) => {
      if (extA.created_at > extB.created_at) {
        return -1;
      }
      if (extA.created_at < extB.created_at) {
        return 1;
      }
      return 0;
    });

    return res.json(transfer.splice(0, 5));
  },
};
