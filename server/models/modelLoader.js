var mongoose = require('mongoose');

var Schema = mongoose.Schema,
    ObjectId = Schema.ObjectId;

var mongo_user = new Schema({
    name    : String,
    uuid    : String,
    user    : Object
});

var User = new Schema({
    name                : String,
    uuid                : String,
    rank                : String,
    ips                 : [String],
    initial_join_date   : Date,
    last_online_date    : Date,
    password            : String,
    profile_about       : String,
    profile_locked      : Boolean,
    profile_locked_by   : mongo_user,

    server              : ObjectId,
    last_online_website : Date,
    posts               : [ObjectId],

    forum_ban           : Boolean,
    forum_banned_by     : mongo_user,
    forum_banned_date   : Date
});
mongoose.model('users', User);

var server = new Schema({
    owner               : String,
    name                : String,
    motd                : String,
    max_players         : Number,
    max_plugins         : Number,
    ram                 : Number,
    creation_date       : Date,
    votes               : Number,
    unique_players      : [String],
    player_count        : Number,
    ip                  : String,
    port                : Number,
    last_online_date    : Date,
    online              : Boolean,
    players             : [String],
    vote_count          : Number,
    web_page_index      : String,
    web_page_locked     : Boolean,
    third_party         : Boolean,
    verified_droplet    : Boolean
});
mongoose.model('server', server);



