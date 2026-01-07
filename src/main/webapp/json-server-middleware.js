module.exports = (req, res, next) => {
  // /api/ann/identification - RID to PID 변환
  if (req.method === 'POST' && req.path === '/api/ann/identification') {
    const { ridList } = req.body;
    const db = require('./db.json');

    if (ridList && ridList.length > 0) {
      const rid = ridList[0];
      const pid = db.identification[rid] || ["00000001"];
      return res.json(pid);
    }

    return res.status(400).json({ error: 'ridList is required' });
  }

  // /api/ann/irb-rid - IRB별 환자 목록
  if (req.method === 'POST' && req.path === '/api/ann/irb-rid') {
    const { irb } = req.body;
    const db = require('./db.json');

    const patients = db.irbPatients[irb] || db.irbPatients.default;
    return res.json(patients);
  }

  // /api/ann/rex - GID to RID 변환
  if (req.method === 'POST' && req.path === '/api/ann/rex') {
    const { irbNo, data } = req.body;

    if (!data || data.length === 0) {
      return res.json({
        irbNo: irbNo,
        data: []
      });
    }

    const responseData = data.map(item => {
      const gid = item.gid;
      if (gid && gid.length >= 4) {
        const suffix = gid.substring(gid.length - 4);
        return { rid: `RID-001-${suffix}` };
      }
      return { rid: 'RID-001-0001' };
    });

    return res.json({
      irbNo: irbNo,
      data: responseData
    });
  }

  // 다른 요청은 기본 JSON Server 동작으로 넘김
  next();
};
