require 'net/http'
require 'nokogiri'
require 'reverse_markdown'

require 'open-uri'
doc = Nokogiri::HTML(open("https://raw.githubusercontent.com/find-sec-bugs/find-sec-bugs/master/plugin/src/main/resources/metadata/messages.xml"))

patterns = doc.xpath("//bugpattern")

ReverseMarkdown.config do |config|
  config.unknown_tags     = :bypass
  config.github_flavored  = true
end

patterns.each do |pattern|

  patternId = pattern.attr("type")
  explanation = pattern.xpath("details").first

  markdown = ReverseMarkdown.convert(explanation.to_s).chomp(']]\> ') # because markdown parse leaves ']]\> ' in the end

  File.write("../src/main/resources/docs/description/#{patternId}.md", markdown)

end