import * as Yup from 'yup';
import Partners from '../models/Partners';

export default {
  async index(req, res) {
    const schema = Yup.object().shape({
      latitude: Yup.number().required(),
      longitude: Yup.number().required(),
    });

    if (!(await schema.isValid(req.query))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { latitude, longitude } = req.query;

    const partners = await Partners.find({
      location: {
        $nearSphere: {
          $geometry: {
            type: 'Point',
            coordinates: [longitude, latitude],
          },
          $maxDistance: 3000,
        },
      },
    });

    partners.map((partner) => {
      Object.assign(partner, {
        _id: undefined,
        balance: undefined,
        password: undefined,
        registration_store: undefined,
      });
      return partner;
    });

    return res.json(partners);
  },
};
