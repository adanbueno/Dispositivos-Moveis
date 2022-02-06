import User from '../models/User';
import Transfer from '../models/Transfer';
import VirtualChange from '../models/VirtualChange';

export default {
  async index(req, res) {
    const { id } = req.user_id;

    const user = await User.findById(id);

    const transferPositive = await Transfer.find({ id_recipient: id })
      .populate({ path: 'id_sender', select: 'name' })
      .exec();

    let resultTransferPositive;
    if (transferPositive) {
      resultTransferPositive = transferPositive.map((transfer) => {
        const result = {};
        const { _id, value, created_at } = transfer;
        const { name } = transfer.id_sender;
        Object.assign(result, { _id, name, value, created_at });
        return result;
      });
    }

    const transferNegative = await Transfer.find({ id_sender: id })
      .populate({ path: 'id_recipient', select: 'name' })
      .exec();

    let resultTransferNegative;
    if (transferNegative) {
      resultTransferNegative = transferNegative.map((transfer) => {
        const result = {};
        const { _id, value, created_at } = transfer;
        const { name } = transfer.id_recipient;
        Object.assign(result, { _id, name, value: -value, created_at });
        return result;
      });
    }

    const virtualChange = await VirtualChange.find({ cpf: user.cpf })
      .populate({ path: 'id_store', select: 'name' })
      .exec();

    let resultVirtualChange;
    if (virtualChange) {
      resultVirtualChange = virtualChange.map((transfer) => {
        const result = {};
        const { _id, value, created_at } = transfer;
        const { name } = transfer.id_store;
        Object.assign(result, { _id, name, value, created_at });
        return result;
      });
    }

    const extracts = [
      ...resultTransferPositive,
      ...resultTransferNegative,
      ...resultVirtualChange,
    ];

    extracts.sort((extA, extB) => {
      if (extA.created_at > extB.created_at) {
        return -1;
      }
      if (extA.created_at < extB.created_at) {
        return 1;
      }
      return 0;
    });

    return res.json(extracts);
  },
};
