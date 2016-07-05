var express = require('express');
var router = express.Router();

var mongoose = require('mongoose');
var User = mongoose.model('users');
var Server = mongoose.model('server');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/status/bukkit', function(req, res, next) {
  if(req.body.key) {
    console.log('key: ' + req.body.key);
    Server.findOne({droplet_key: req.body.key}, function(err, server) {
      if(!server) {
        console.log('Unable to find server with matching key.');
        return res.end('Unable to find server with matching key.');
      }

      var icon_material = "DIAMOND";
      if(req.body.icon_material) {
        icon_material = req.body.icon_material;
      }

      Server.update({_id: server._id}, {
        $set: {
          third_party: true,
          last_online_date: new Date(),
          online: true,
          player_count: req.body.player_count,
          max_players: req.body.max_players,
          ip: req.body.ip,
          port: req.body.port,
          motd: req.body.motd,
          icon_material: icon_material
        }
      }, function(err) {
        console.log('Updated ' + server.name);
        return res.end('success');
      })

    })
  } else {
    res.end('Key was not included in request.');
  }
})

module.exports = router;
