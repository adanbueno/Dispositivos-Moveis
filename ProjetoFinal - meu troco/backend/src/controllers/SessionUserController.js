import jwt from 'jsonwebtoken';
import * as Yup from 'yup';
import User from '../models/User';
import authConfig from '../config/auth';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      email: Yup.string().email().required(),
      password: Yup.string().required(),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { email, password } = req.body;
    const user = await User.findOne({ email });

    if (!user || !(await user.comparePassword(password))) {
      return res.status(401).json({ error: 'Invalid data' });
    }

    const { _id, name, balance, cpf } = user;

    const { secret, expiresIn } = authConfig.jwt;

    return res.json({
      user: {
        _id,
        name,
        email,
        cpf,
        balance,
      },
      token: jwt.sign({ id: _id }, secret, {
        expiresIn,
      }),
    });
  },
};
