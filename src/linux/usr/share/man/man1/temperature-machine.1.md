temperature-machine(1) -- The homebrew data logger
==================================================

## SYNOPSIS

`temperature-machine` `-s`|`--server` 
`temperature-machine` `-c`|`--client`

## DESCRIPTION

**temperature-machine** logs temperature data from one or more DS18b20 temperature probes and displays some pretty graphs.

Run on multiple machines in a client-server model.

## FIND OUT MORE

See the `temperature-machine` website at []().

## OPTIONS

These options control whether output is written to file(s), standard output, or
directly to a man pager.

  * `-m`, `--man`:
    Don't generate files, display <file>s as if man(1) were invoked on the roff
    output file. This simulates default man behavior by piping the roff output
    through groff(1) and the paging program specified by the `MANPAGER`
    environment variable.

  * `-S`, `--server`:
    Don't generate files, start an HTTP server at <http://localhost:1207/> and
    serve dynamically generated HTML for the set of input <file>s. A file named
    *example.2.temperature-machine* is served as */example.2.html*. There's also an index page
    at the root with links to each <file>.

    The server respects the `--style` and document attribute options
    (`--manual`, `--date`, etc.). These same options can be varied at request
    time by giving them as query parameters: `?manual=FOO&style=dark,toc`

    *NOTE: The builtin server is designed to assist in the process of writing
    and styling manuals. It is in no way recommended as a general purpose web
    server.*

  * `--pipe`:
    Don't generate files, write generated output to standard output. This is the
    default behavior when temperature-machine source text is piped in on standard input and no
    <file> arguments are provided.

Format options control the files `temperature-machine` generates, or the output format when the
`--pipe` argument is specified. When no format options are given, both `--roff`
and `--html` are assumed.

  * `-r`, `--roff`:
    Generate roff output. This is the default behavior when no <file>s are given
    and temperature-machine source text is read from standard input.

  * `-5`, `--html`:
    Generate output in HTML format.

  * `-f`, `--fragment`:
    Generate output in HTML format but only the document fragment, not the
    header, title, or footer.

Document attributes displayed in the header and footer areas of generated
content are specified with these options. (These values may also be set via
the [ENVIRONMENT][].)

  * `--manual`=<manual>:
    The name of the manual this man page belongs to; <manual> is prominently
    displayed top-center in the header area.

  * `--organization`=<name>:
    The name of the group, organization, or individual responsible for
    publishing the document; <name> is displayed in the bottom-left footer area.

  * `--date`=<date>:
    The document's published date; <date> must be formatted `YYYY-MM-DD` and is
    displayed in the bottom-center footer area. The <file> mtime is used when no
    <date> is given, or the current time when no <file> is available.

HTML output can be customized through the use of CSS stylesheets:

  * `--style`=<module>[,<module>]...:
    The list of CSS stylesheets to apply to the document. Multiple <module>
    arguments may be specified, but must be separated by commas or spaces.

    When <module> is a simple word, search for files named <module>`.css` in all
    directories listed in the [`temperature-machine_STYLE`](#ENVIRONMENT) environment variable,
    and then search internal styles.

    When <module> includes a _/_ character, use it as the full path to a
    stylesheet file.

    Internal styles are _man_ (included by default), _toc_, and _80c_.  See
    [STYLES][] for descriptions of features added by each module.

Miscellaneous options:

  * `-w`, `--warnings`:
    Show troff warnings on standard error when performing roff conversion.
    Warnings are most often the result of a bug in temperature-machine's HTML to roff conversion
    logic.

  * `-W`:
    Disable troff warnings. Warnings are disabled by default. This option can be
    used to revert the effect of a previous `-w` argument.

  * `-v`, `--version`:
    Show temperature-machine version and exit.

## LINK INDEXES

When generating HTML output, `temperature-machine` hyperlinks manual references (like
`grep(1)`, `ls(1)`, `markdown(7)`) in source text based on reference name to URL
mappings defined in an `index.txt` file. Each line of the index file describes a
single reference link, with whitespace separating the reference's <id> from its
<location>. Blank lines are allowed; lines beginning with a `#` character are
ignored:

    # manuals included in this project:
    whisky(1)    whisky.1.temperature-machine
    tango(5)     tango.5.temperature-machine

    # external manuals
    grep(1)      http://man.cx/grep(1)
    ls(1)        http://man.cx/ls(1)

    # other URLs for use with markdown reference links
    src          http://github.com/

The <location> is an absolute or relative URL that usually points at an HTML
version of manpage. It's possible to define references for things that aren't
manpages.

All manuals in an individual directory share the references defined in that
directory's `index.txt` file. Index references may be used explicitly in
Markdown reference style links using the syntax: `[`<text>`][`<id>`]`, where
<text> is the link text and <id> is a reference name defined in the index.

## STYLES

The `--style` option selects a list of CSS stylesheets to include in the
generated HTML. Styles are applied in the order defined, so each can use the
cascade to override previously defined styles.

### Builtin Stylesheets

These styles are included with the distribution:

   * `man`:
     Basic manpage styles: typography, definition lists, indentation. This is
     always included regardless of `--style` argument. It is however possible to
     replace the default `man` module with a custom one by placing a `man.css`
     file on the `temperature-machine_STYLE` path.

   * `print`:
     Basic print stylesheet. The generated `<style>` tag includes a
     `media=print` attribute.

   * `toc`:
     Enables the Table of Contents navigation. The TOC markup is included in
     generated HTML by default but hidden with an inline `display:none` style
     rule; the `toc` module turns it on and applies basic TOC styles.

   * `dark`:
     Light text on a dark background.

   * `80c`:
     Changes the display width to mimic the display of a classic 80 character
     terminal. The default display width causes lines to wrap at a gratuitous
     100 characters.

### Custom Stylesheets

Writing custom stylesheets is straight-forward. The following core selectors
allow targeting all generated elements:

   * `.mp`:
     The manual page container element. Present on full documents and document
     fragments.

   * `body#manpage`:
     Signifies that the page was fully-generated by temperature-machine and contains a single
     manual page (`.mp` element).

   * `.man-decor`:
     The three-item heading and footing elements both have this class.

   * `.man-head`, `.man-foot`:
     The heading and footing, respectively.

   * `.man-title`:
     The main `<h1>` element. Hidden by default unless the manual has no <name>
     or <section> attributes.

See the [builtin style sources][builtin] for examples.

[builtin]: http://github.com/rtomayko/temperature-machine/tree/master/lib/temperature-machine/template
          "Builtin Stylesheet .css files"

## EXAMPLES

Build roff and HTML output files and view the roff manpage using man(1):

    $ temperature-machine some-great-program.1.temperature-machine
    roff: some-great-program.1
    html: some-great-program.1.html
    $ man ./some-great-program.1

Build only the roff manpage for all `.temperature-machine` files in the current directory:

    $ temperature-machine --roff *.temperature-machine
    roff: mv.1
    roff: ls.1
    roff: cd.1
    roff: sh.1

Build only the HTML manpage for a few files and apply the `dark` and `toc`
stylesheets:

    $ temperature-machine --html --style=dark,toc mv.1.temperature-machine ls.1.temperature-machine
    html: mv.1.html
    html: ls.1.html

Generate roff output on standard output and write to file:

    $ temperature-machine <hello.1.temperature-machine >hello.1

View a temperature-machine file in the same way as man(1) without building a roff file:

    $ temperature-machine --man hello.1.temperature-machine

Serve HTML manpages at <http://localhost:1207/> for all `*.temperature-machine` files
under a `man/` directory:

    $ temperature-machine --server man/*.temperature-machine
    $ open http://localhost:1207/


## ENVIRONMENT

  * `temperature-machine_MANUAL`:
    A default manual name to be displayed in the top-center header area.
    The `--manual` option takes precedence over this value.

  * `temperature-machine_ORGANIZATION`:
    The default manual publishing group, organization, or individual to be
    displayed in the bottom-left footer area. The `--organization` option takes
    precedence over this value.

  * `temperature-machine_DATE`:
    The default manual date in `YYYY-MM-DD` format. Displayed in the
    bottom-center footer area. The `--date` option takes precedence over this
    value.

  * `temperature-machine_STYLE`:
    A `PATH`-style list of directories to check for stylesheets given to the
    `--style` option. Directories are separated by a _:_; blank entries are
    ignored. Use _._ to include the current working directory.

  * `MANPAGER`:
    The paging program used for man pages. This is typically set to
    something like 'less -is'.

  * `PAGER`:
    Used instead of `MANPAGER` when `MANPAGER` is not defined.

## BUGS

**temperature-machine** is written in Ruby and depends on hpricot and rdiscount, extension
libraries that are non-trivial to install on some systems. A more portable
version of this program would be welcome.

## COPYRIGHT

temperature-machine is Copyright (C) 2009 Ryan Tomayko <http://tomayko.com/about>

## SEE ALSO

groff(1), man(1), pandoc(1), manpages(5), markdown(7), roff(7), temperature-machine-format(7)