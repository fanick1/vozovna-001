# Informační koutek #

### Obecné ###
sloveso hodit (výjimku) se časuje nepravidelně:
throw - threw - **thrown**


### Dao ###

**Vždy zavírat entity manager** ([link1](http://www.objectdb.com/java/jpa/persistence/overview#EntityManager_), [link2](http://javanotepad.blogspot.cz/2007/06/how-to-close-jpa-entitymanger-in-web.html))

Je třeba mít ošetřeno, že pokud si sami vytváříme instanci EntityManageru, tak ji potom i zavřeme a tím uvolníme zdroje, které EntityManager používá (např. připojení k databázi). Abychom měli jistotu, že kód se zavíráním Entity Manageru proběhne, je dobré ho mít ve finally bloku.

takto ne:
```
public User getById(Long id) {
    // ošetření emf a parametrů
    EntityManager manager = this.factory.createEntityManager();
    User user = manager.find(User.class, id);
    manager.close();
    return user;
}
```

takto ano:
```
public Drive getById(Long id) {
    // ošetření emf a parametrů
    EntityManager em = this.emf.createEntityManager();
    Drive result = null;
    try {
        result = em.find(Drive.class, id);
    } catch (RuntimeException e) {
        throw e;
    } finally {
        em.close();
    }
    return result;
}
```

**Psaní query Stringů**
Pokud to jde, tak do Stringů nepíšeme jméno třídy napevno, aby se po případném refaktoringu nemusely dotazy procházet a opravovat.

("FROM " + ServiceInterval.class.getName() + " interval ... ")



### Testy ###

**odchytávání obecných Exception**

Mít test celý obalený v try-catch bloku a při výjimce test selhávat pomocí Assert.fail() je zbytečné. Pokud je během testu vyhozena neodchycená výjimka, test selže automaticky.

Tohle je zbytečné, odmazat:
```
try {
// něco něco
catch (Exception ex) {
            Assert.fail("Unexpected exception throwed: " + ex + " " + ex.getMessage());
}
```