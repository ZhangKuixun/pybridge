# from __future__ import unicode_literals
import sys
import os
# if __package__ is None and not hasattr(sys, 'frozen'):
# import os.path
# path = os.path.realpath(os.path.abspath(__file__))
# print("pathpath="+path)
# sys.path.insert(0, os.path.dirname(os.path.dirname(path)))
# sys.path.insert(0, os.path.dirname(path))
# print("pathpath---"+os.path.dirname(os.path.dirname(path)))


# dirname = os.path.dirname(os.path.realpath(__file__))
# print("pathpath---" + dirname)
# a_ = dirname + '/libs/' + 'arm64-v8a/select.so'
# sys.path.insert(0, a_)
# print("pathpath---" + a_)


sys.path.insert(0, os.path.dirname(os.path.realpath(__file__)) + '/libs/armeabi')


import youtube_dl # this error
# from youtube_dl import YoutubeDL
import json



def router(args):
    """
    Defines the router function that routes by function name.

    :param args: JSON arguments
    :return: JSON response
    """
    # print("args: %s" % args)
    values = json.loads(args)
    # print("values: %s" % values)

    try:
        function = routes[values.get('function')]
        # print("function: %s" % function)

        status = 'ok'
        res = function(values)
        # print("res: %s" % res)
    except KeyError:
        status = 'fail'
        res = None

    return json.dumps({
        'status': status,
        'result': res,
    })


def greet(args):
    """Simple function that greets someone."""
    return 'Hello %s' % args['name']


def add(args):
    """Simple function to add two numbers."""
    return args['a'] + args['b']


def mul(args):
    """Simple function to multiply two numbers."""
    return args['a'] * args['b']


routes = {
    'greet': greet,
    'add': add,
    'mul': mul,
}
