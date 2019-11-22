var tehtavat = [];
document.addEventListener("DOMContentLoaded", init);
function init() {
    document.getElementById("nappi").addEventListener("click", hae);
    document.getElementById("painike").addEventListener("click", lisaa);
    document.getElementById("muuta").addEventListener("click", muokkaa);
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
        todolista.innerHTML = "<tr>" + "<th>" + "ID" + "<th>" + "TEHTAVA" + "</th>" + "</tr>";
        for (var i = 0; i < tehtavat.length; i++) {
            var t = tehtavat[i];
            console.dir(t);
            todolista.innerHTML += "<tr>" + "<td>" + "" + t.id + "" + "<td>" + "" + t.tehtava + "" + "</td>" + "</tr>";
        }
        document.getElementById("taulukonkoko").innerHTML = "Sinulla on " + tehtavat.length + " tekemätöntä tehtävää.";
    }
}

function lisaa() {
    var lisattava = document.getElementById("lisattava").value;
    axios.post('api/todot', {
        tehtava: lisattava,
    })
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
    document.getElementById("lisattava").value = "";
    document.getElementById("lisatty").innerHTML = "Tehtävä on lisätty listalle. Päivitä tehtävälista klikkaamalla 'Hae tehtävät'."
}

function muokkaa() {
    var id = document.getElementById("muokattavanid").value;
    var muokattava = document.getElementById("muokattava").value;
    axios.put('api/todot/' + id,{
        id: id, tehtava: muokattava,
    })
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
    document.getElementById("muokattavanid").value = "";
    document.getElementById("muokattava").value = "";
    document.getElementById("muutettu").innerHTML = "Tehtävä on muokattu. Päivitä tehtävälista klikkaamalla 'Hae tehtävät'."
}

    function poista() {
    var poistettava = document.getElementById("poistettava").value; //kenttään käyttäjän syöttämä numero

        axios.delete('api/todot/' + poistettava,{
            id: poistettava,
        }).then(function(response) {
            console.log("Deleted:");
        }).catch(function (error) {
            console.log("Deletion failed with error:" + error);
        });
        document.getElementById("poistettava").value = "";
        document.getElementById("onnittelu").innerHTML = "Tehtävä on poistettu. Päivitä tehtävälista klikkaamalla 'Hae tehtävät'."

    }
/* Tämä oli poista metodin sisällä kun yritin  saada tulostumaan herjaa mikäli idtä ei löydy.
Oli ennen axios.deleteä, jotta tarkistaisi listan ennen tehtävän poistoa. Mutta ei onnistunut, tulee aina vain että väärä id
axios.get('api/todot/kaikki') //kokeillaan onnistuuko listan haku ja tarkistus, onko annettu id listalla. sitten poisto
        .then(function (response) {
            console.log(response);
            tehtavat = response.data;
            tarkista(tehtavat);
        })
        .catch(function (error) {
            console.log(error);
        });

    function tarkista(tehtavat){
        if (tehtavat.includes(poistettava)){
            document.getElementById("onnittelu").innerHTML = "Hienoa, tehtävälistasi on yhden tehtävän verran lyhyempi!";
        } else {
            document.getElementById("onnittelu").innerHTML = "Annetulla id:llä ei löytynyt tehtävää. Tarkista poistettavan tehtävän id taulukosta."
        }
        }

    for (var i = 0; i < tehtavat.length; i++) {
        var t = tehtavat[i];

        if (poistettava === t.id) {
            document.getElementById("onnittelu").innerHTML = "Hienoa, tehtävälistasi on yhden tehtävän verran lyhyempi!";
        } else {
            document.getElementById("onnittelu").innerHTML = "Annetulla id:llä ei löytynyt tehtävää. Tarkista poistettavan tehtävän id taulukosta."
        }
    }
    }*/



