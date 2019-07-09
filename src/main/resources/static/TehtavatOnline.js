var tehtavat = [];
document.addEventListener("DOMContentLoaded", init);
function init() {
    document.getElementById("nappi").addEventListener("click", hae);
    document.getElementById("painike").addEventListener("click", lisaa);
    document.getElementById("dellaa").addEventListener("click", poista);
}
function hae() {
    axios.get('api/todot/kaikki')
        .then(function (response) {
            console.log(response);
            tehtavat = response.data;
            suorita(tehtavat);
        })
        .catch(function (error) {
            console.log(error);
        });                                     //Tähän asti todennäköisesti toimii hae() funktio, joka alkaa riviltä 6. myös init ok.

    function suorita(tehtavat) {
        var todolista = document.getElementById("todolista");
        todolista.innerHTML = "<tr>" + "<th>" + "Id" + "<th>" + "Tehtävä" + "</th>" + "</tr>";
        for (var i = 0; i < tehtavat.length; i++) {
            var t = tehtavat[i];
            console.dir(t);
            todolista.innerHTML += "<tr>" + "<td>" + t.id + "<td>" + t.tehtava + "</td>" + "</tr>";
        }
        document.getElementById("taulukonkoko").innerHTML = "Sinulla on " + tehtavat.length + " tekemätöntä tehtävää.";
    }
}
    function lisaa() {
        var lisattava = document.getElementById("lisattava").value;
        axios.post('/api/todot', {
            tehtava: lisattava,
        })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    function poista() {
        var poistettava = document.getElementById("poistettava").value;
        axios.delete('api/todot/' + poistettava,{
            id: poistettava,
        }).then(function(response) {
            console.log("Deleted:");
        }).catch(function (error) {
            console.log("Deletion failed with error:" + error);
        });
    }



