# Översikt Tidbokning 1.1 #

Nedanstående tabell visar på de kontrakt som ingår i version 1.1.

För ytterligare detaljer kring kontrakten och vad som skiljer sig mellan 1.0 och 1.1, Ladda ner [version 1.1](http://rivta.se/downloads/TD_SCHEDULING_1_1_1_R.zip).

| **Kontrakt** | **Syfte** | **Förändring i denna version** |
|:-------------|:----------|:---------------------------------|
| GetSubjectOfCareSchedule | Listar en invånares bokningar | kan visa kallelser |
| GetAvailableDates | Listar datum med bokningsbara tider utifrån sökparametrar | Subject\_of\_care som urvalsparametrar |
| GetAvailableTimeslots | Listar bokningsbara tider utirån sökparametrar | Subject\_of\_care som urvalsparametrar |
| GetBookingDetails | Visa komplett information om en bokning | start och sluttid frivilligt, kan visa kallelser |
| CancelBooking | Avboka invånare från bokning, orsak kan anges |  |
| UpdateBooking | Ändra tid för en bokning | namn skickas |
| MakeBooking | Ny bokning | namn skickas |
| GetAllTimeTypes | Listar tillgängliga tidtyper för nybokning | careType, performer, Subject\_of\_care som urvalsparametrar |
| GetAllHealthcareFacilities | Listar enheter som invånare kan boka om till. | Ny för Tidbok 1.1 |
| GetAllPerformers | Listar utförare (medarbetare) | Ny för Tidbok 1.1 |
| GetAllCareTypes | Listar vårdtyper för nybokning | Ny för Tidbok 1.1 |


