/*
 * Received LED status message, then apply the status. 
 *
 * @author Victor Benarbia
 */
var mqtt = require('mqtt')

client = mqtt.createClient(1883, 'localhost');

var exec = require('child_process').exec;

client.subscribe('k8055');

client.on('message', function (topic, message) 
{
  console.log(message);

  var msg = JSON.parse(message);
  exec('k8055 -d:' + msg.led, function(err, stdout, stderr)
  {
 //  console.log('stdout: ' + stdout);
 //  console.log('stderr: ' + stderr);
    if (err !== null) {
      console.warn('exec error: ' + err);
    }
  });
});

client.on('error', function(err) {
    client.stream.end();
    console.log('error!', err);
  });

console.log("Ready...");
//client.end();

