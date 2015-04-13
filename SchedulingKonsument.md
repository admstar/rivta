# Introduktion #

I dagsläget finns en (1) konsument av tidbokninginformation, Mina vårdkontakter.

Mina vårdkontakter stödjer i dagsläget fyra flöden inom Tidbokning, framtagna för Tidbokning 1.0, se tabellen nedan.

| **Flöden i MVK** | **Kräver kontrakt** |
|:------------------|:---------------------|
| Visa bokade tider | GetSubjectOfCareSchedule <br> GetBookingDetails <br>
<tr><td> Nybokning </td><td> GetAllTimeTypes <br> GetAvailableDates <br> GetAvailableTimeslot <br> MakeBooking <br> GetAllHealthcareFacilities (frivillig) </td></tr>
<tr><td> Omboka </td><td> GetSubjectOfCareSchedule <br> GetBookingDetails <br> GetAvailableDates <br> GetAvailableTimeslot <br> UpdateBooking <br> GetAllHealthcareFacilities (frivillig) </td></tr>
<tr><td> Avboka </td><td> GetSubjectOfCareSchedule <br> GetBookingDetails <br> CancelBooking </td></tr></tbody></table>

<h2>Stöd för Tidbokning 1.1</h2>

Mina vårdkontakter stödjer inte Tidbokning 1.1 fullt ut. De tjänster som stöds, se tabellen ovan, är uppgraderad enligt Tidbokning 1.1. En del ny funktionalitet som finns i Tidbokning 1.1 stöds inte av Mina vårdkontakter, ex ombudsbokning, lista vårdtyper.<br>
<br>
Mer information om Mina vårdkontakter:<br>
<ul><li><a href='http://mvkforum.se'>MVK Forum</a>
</li><li>Kontakt: minavardkontakter (at) sll.se