/*
 * Publish LED status update messages. 
 * 
 * @author Victor Benarbia / vikoky
 */
var mqtt = require('mqtt')

client = mqtt.createClient(1883, '192.168.1.79');

var n = 0;

setInterval(function()
{
     n++;
     var msg = {led:n};
     if(n > 255)
     {
       clearInterval();
       console.log("Shutdown...");
       client.end();
     }
     else
     {
       client.publish('k8055', JSON.stringify(msg));
       console.log('Send: %s', JSON.stringify(msg));
     }
}, 1000);

client.on('error', function(err) {
    client.stream.end();
    console.log('error!', err);
  });

