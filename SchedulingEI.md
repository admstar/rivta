## Policy för uppdatering av tidbokningsinformation i Engagemangsindex ##

Varje tjänstedomän har ansvar att definiera policy och regler för uppdatering och hantering av information inom Engagemangsindex.

För tjänstedomänen tidbokning gäller följande policy:

  1. **Vid ändringar i tidboken ska producenten uppdatera Engagemangsindex.** Detta avser nybokning, elektronisk kallelse skapas, ombokning och avbokning. Beroende på förutsättningar och implementation hos producenten så kan en ombokning innebära att befintlig post tas bort och en ny skapas.
  1. **Endast ändringar för mottagningar som är anslutna till webbtidbokning ska skickas till Engagemangsindex.** En konsument som blir notifierad om en ändring via EI, ska kunna anropa en producent enligt tjänstekontraktet getBookingDetails och få ut fullständig information om bokningen/kallelsen.
  1. **Endast aktuella/relevanta händelser ska finnas i EI.** Historiken ska rensas från EI av producenten. Producenten ska rensa information som inte längre är relevant, ex: när en bokning är passerad, när en kallelse inte längre är giltig. Regelverket för när informationen inte är relevant kan skilja sig mellan olika verksamheter och styrs från producenten.
  1. **En bokad tid eller kallelse i tidbokningssystemet ska motsvaras av en post i engagemangsindex.**
  1. **Återladdning av EI ska kunna genomföras av en producent.** Detta är en reservrutin som behövs om Engagemangsindex blir korrupt.

För mer utförlig beskrivning och kompletterande användningsfall:
> http://rivta.googlecode.com/svn/ServiceInteractions/riv/crm/scheduling/wiki/Tidbokning_Engagemangsindex.pdf