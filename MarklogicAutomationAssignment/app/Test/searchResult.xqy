
xdmp:set-response-content-type("text/html"),
('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">',
<xhtml:html xmlns:xhtml="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <title>Speech Results</title>
  </head>
  <xhtml:body>
    {
let $val := xdmp:get-request-field("query")
	for $x in cts:search(doc()/*, $val)
  return 
    cts:highlight($x, $val, <b>{$cts:text}</b>)
	
    }
  </xhtml:body>
</xhtml:html>)