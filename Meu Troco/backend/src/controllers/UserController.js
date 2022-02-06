import * as Yup from 'yup';
import User from '../models/User';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      name: Yup.string().required(),
      cpf: Yup.string().required(),
      email: Yup.string().email().required(),
      password: Yup.string().required().min(6),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { email, cpf } = req.body;

    const user_email = await User.findOne({ email });
    if (user_email) {
      return res.status(400).json({ error: 'Email already exists.' });
    }

    const user_cpf = await User.findOne({ cpf });
    if (user_cpf) {
      return res.status(400).json({ error: 'CPF already exists.' });
    }

    const user = await User.create(req.body);
    Object.assign(user, {
      cpf: undefined,
      password: undefined,
    });

    return res.json(user);
  },

  async update(req, res) {
    const schema = Yup.object().shape({
      name: Yup.string(),
      email: Yup.string().email(),
      oldPassword: Yup.string().min(6),
      password: Yup.string()
        .min(6)
        .when('oldPassword', (oldPassword, field) =>
          oldPassword ? field.required() : field
        ),
      confirmPassword: Yup.string().when('password', (password, field) =>
        password ? field.required().oneOf([Yup.ref('password')]) : field
      ),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { email, oldPassword } = req.body;
    const { id } = req.user_id;
    let user = await User.findById(id);

    if (email && email !== user.email) {
      const userExist = await User.findOne({ email });
      if (userExist) {
        return res.status(400).json({ error: 'Email already exists.' });
      }
    }

    if (oldPassword && !(await user.comparePassword(oldPassword))) {
      return res.status(401).json({ error: 'Password does not match.' });
    }

    delete req.body.cpf;
    delete req.body.balance;

    user = await User.findByIdAndUpdate({ _id: id }, req.body, { new: true });

    Object.assign(user, { password: undefined });

    return res.json(user);
  },

  async index(req, res) {
    const { id } = req.user_id;

    const user = await User.findById(id);

    return res.json(user);
  },
};
