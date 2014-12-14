/***
 * Debug C++ wrapper directly.
 */

var k8055 = require('./build/Release/k8055');
k8055.connect(0);

function random()
{
 return Math.floor(Math.random() * 255 + 0);
}

var delay = 100;
console.log("Set delay: " + delay);

var counter = 0;
setInterval(function()
{
 if(counter++ == 100) 
 {
   clearInterval();
   k8055.disconnect();
   console.log("Disconnect");
 }
 else
 {
   var d = k8055.getDigital();
   console.log("read: " + d);
   k8055.setDigital(d);
 }
}, delay);

