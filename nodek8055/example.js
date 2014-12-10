var k8055 = require('./build/Release/hello');
k8055.connect(0);

function random()
{
 return Math.floor(Math.random() * 255 + 0);
}

var delay = 10;
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
  k8055.setDigital(random());
}, delay);

