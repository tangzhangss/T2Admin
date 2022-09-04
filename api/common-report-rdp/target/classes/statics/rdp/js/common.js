//convert string to xml object 
function String2XML(xmlstring) { 
// for IE 
if (window.ActiveXObject) { 
var xmlobject = new ActiveXObject("Microsoft.XMLDOM"); 
xmlobject.async = "false"; 
xmlobject.loadXML(xmlstring); 
return xmlobject; 
} 
// for other browsers 
else { 
var parser = new DOMParser(); 
var xmlobject = parser.parseFromString(xmlstring, "text/xml"); 
return xmlobject; 
} 
} 
//convert xml object to string 
function XML2String(xmlobject) { 
// for IE 
if (window.ActiveXObject) { 
return xmlobject.xml; 
} 
// for other browsers 
else { 
return (new XMLSerializer()).serializeToString(xmlobject); 
} 
} 

var parseXml;

if (typeof window.DOMParser != "undefined") {
    parseXml = function(xmlStr) {
        return ( new window.DOMParser() ).parseFromString(xmlStr, "text/xml");
    };
} else if (typeof window.ActiveXObject != "undefined" &&
       new window.ActiveXObject("Microsoft.XMLDOM")) {
    parseXml = function(xmlStr) {
        var xmlDoc = new window.ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = "false";
        xmlDoc.loadXML(xmlStr);
        return xmlDoc;
    };
} else {
    throw new Error("No XML parser found");
}