xquery version "1.0-ml";
(: dump.xqy :)
declare namespace bk = "http://www.marklogic.com/ns/gs-books";

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Database dump</title>
  </head>
  <body>
  <b>XML Content</b>
{
    for $book in doc("books.xml")/bk:books/bk:book
    order by $book
	return
    <pre>
        Title: { $book/bk:title/text() }
        Author: { ($book/bk:author/text(), " ",
        $book/bk:author/bk:last/text()) }
        Publisher: { $book/bk:publisher/text() }
    </pre>
	
}
    <a href="update-form.xqy">Update Publisher</a>
  </body>
</html>