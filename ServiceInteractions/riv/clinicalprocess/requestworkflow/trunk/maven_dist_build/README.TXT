This directory exist to create different zipfile(s) for distribution.
To build the full distribution(requeststatus and processrequest et al) you need to execute the following command.

$ mvn -Pcombined clean package

To build the SLL eRemiss pilot distribution execute the following command.

$ mvn -Psll_pilot clean package
