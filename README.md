Tämä sovellus vastaanottaa, varastoi ja optimoi teollisuuden käyttäämää teräsprofiilia. 

Profiili saapuu vastaanottajalle (sovelluksen 1. käyttäjä) ja vastaanottaja kirjaa, sekä tallentaa järjestelmään profiilin
tiedot: sulatusnumeron (=profiilin yksilöivä, valmistusvaiheessa annettu numerosarja) , pituuden (mm) sekä varastopaikan. 
Vastaanottamiselle on siis sovelluksessa oma näkymänsä.

Sovelluksen 2. käyttäjä eli tuotannon suunnittelija käyttää sovelluksen toista eli tuotantopuolta/-näkymää. Hänen tehtävänään on 
ottaa vastaan tilauksia tuotannolta(tämä ei ole sovelluksen ominaisuus!), kirjoittaa sovellukseen, mitä materiaalia hän tarvitsee 
ja miten paljon, ja sovellus hakee ja optimoi varastosta tarvittavat materiaalit. Lopuksi suunnittelijalle tulostuu raportti
josta ilmenee tarvittavat profiilit ja niiden varastopaikat.

Sovellus siis optimoi kulloisenkin tilauksen varaston tilanteen mukaan. Eli jos tilaus on 8000mm pitkä profiili, se käyttää sitä varten
8000mm profiilin. Jos sitä ei löydy, se käyttää 10000mm profiilin, ei 120000mm pitkää. Yli 1000mm pitkät jäännöspalat se kierrättää takaisin varastoon(tietokantaan) 
ja nämä ovat käytettävissä seuraavilla optimointikierroksilla.




HUOM!
29.11. KAAVIOIDEN EIVÄT OLE PÄIVITETTY AJANTASALLE! TAPAHTUU NELJÄNNESSÄ ITERAATIOSSA!
JOSTAIN SYYSTÄ JACOCO EI GENEROI SITE-KANSIOTA. FIKSATAAN MYÖS SEURAAVAAN ITERAATIOON 
 