/*
 * Application
 */

function MainController($scope, $http)
{
    $scope.connectClass = ''; //class="active" when active
    $scope.connectName = "Connect";
    $scope.connect = function()
    {
        if($scope.connectClass == '')
        {
            console.log("Connecting to the card..");
            $http.get('/connect').success(function(data)
            {
                //console.log(data);
                if(data.status == "ok")
                {
                    $scope.connectName = "Connected";
                    $scope.toggle(true);
                }
                else
                {
                    alert("Error!");
                }
            });
        }
        else
        {
            console.log("Disconnecting to the card..");
            $http.get('/disconnect').success(function(data)
            {
                if(data.status == "ok")
                {
                    $scope.connectName = "Connect";
                    $scope.toggle(false);
                }
                else
                {
                    alert("Error!");
                }
            });
        }
    };

    $scope.toggle = function(isConnected)
    {
      if(isConnected)
          $scope.connectClass = 'active';
       else
          $scope.connectClass = '';
    }

    $scope.resetAllDigital = function()
    {
        var o = [];
        angular.forEach($scope.outputs, function(it)
        {
            it.on = false;
            o.push(it);
        });
        $scope.outputs = o;
        this.writeAll();
    };

    $scope.setAllDigital = function()
    {
        var o = [];
        angular.forEach($scope.outputs, function(it)
        {
            it.on = true;
            o.push(it);
        });
        $scope.outputs = o;
        this.writeAll();

    };

    $scope.resetAllAnalog = function()
    {
        console.log("Reset all analog");
        $scope.da1 = 0;
        $scope.da2 = 0;
        this.writeAll();
    };

    $scope.setAllAnalog = function()
    {
        console.log("Set all analog");
        $scope.da1 = 100;
        $scope.da2 = 100;
        this.writeAll();
    };

    $scope.ad1 = {width: '0%'};
    $scope.ad2 = {width: '0%'};

    $scope.da1 = 0;
    $scope.da2 = 0;

    /* Loop
    $scope.$watch('da1', function()
    {
        $scope.da1Value = {width: $scope.da1 + '%'};
    });
    $scope.$watch('da2', function()
    {
        $scope.da2Value = {width: $scope.da2 + '%'};
    });
      */
    $scope.inputs = [
        {x: 1, on: false},
        {x: 2, on: false},
        {x: 3, on: false},
        {x: 4, on: false},
        {x: 5, on: false}
    ];

    $scope.outputs = [
        {x: 1, on: false},
        {x: 2, on: false},
        {x: 3, on: false},
        {x: 4, on: false},
        {x: 5, on: false},
        {x: 6, on: false},
        {x: 7, on: false},
        {x: 8, on: false}
    ];

    $scope.writeAll = function()
    {
        //TODO Send json
        var v =
        {
            direction:'output',
            digital:$scope.outputs,
            analog:
            {
                da1:$scope.da1,
                da2:$scope.da2
            }
        }

        //console.log("> request: " + JSON.stringify(v));

        $http.get('/apply', {params:{q: v}}).success(function(json)
        {
            if(json.status == "ok")
            {
                console.log("Set all outputs...");
               //notify('Set output values...');
            }
            else
            {
                alert("Error!");
            }
        });
    };

    $scope.refreshAll = function()
    {
        $http.get('/read').success(function(json)
        {
            console.log(JSON.stringify(json));
            if(json.direction == "input")
            {
                $scope.inputs = json.digital;
                $scope.ad1 = {width: json.analog.ad1 + '%'};
                $scope.ad2 = {width: json.analog.ad2 + '%'};
                notify("Read inputs values..")
            }
            else
            {
                alert("Error!");
            }
        });
    };

    $scope.init = function()
    {
        console.log("init..");
        $http.get('/isConnected').success(function(json)
        {
            //console.log(json);
            $scope.toggle(json.isConnected);
            $scope.refreshAll();
            //console.log(JSON.parse(data));

            // Build jQuery Knobs
            $("#da1").knob({
                change: function (value) {
                    //console.log("changed to: " + value);
                    angular.element(document.getElementById('da1')).scope().$apply(function(scope){
                        scope.da1 = value;
                        scope.writeAll();
                    });
                }
            });

            $("#da2").knob({
                change: function (value) {
                    //console.log("changed to: " + value);
                    angular.element($("#da1")).scope().$apply(function(scope){
                        scope.da2 = value;
                        scope.writeAll();
                    });
                }
            });

            $(".dial").knob();

            notify("Application loaded...")

        });
    };
}

//TODO Create websocket service to receive information from server.

function notify(msg)
{
    $.jGrowl(msg);
};
