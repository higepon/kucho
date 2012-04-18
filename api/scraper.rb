require 'rubygems'
require 'mechanize'
require 'yaml'

config = YAML.load_file("#{Dir::pwd}/config.yml")
p config["url"]

