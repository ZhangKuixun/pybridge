from __future__ import unicode_literals

import sys

if __package__ is None and not hasattr(sys, 'frozen'):
    import os.path

    path = os.path.realpath(os.path.abspath(__file__))
    sys.path.insert(0, os.path.dirname(os.path.dirname(path)))


import json
import traceback
import python

def sayHello(url):

    options = {
        'format': 'best',  # 'best[height<=480]/best',#'best',  # choice of qualit
        'nocheckcertificate': True,
        'skip_download': True,
        'youtube_include_dash_manifest': False
    }
    key = ''

    with python.YoutubeDL(options) as ydl:
        try:
            if (len(key) > 2):
                meta = ydl.extract_info(url, download=False, ie_key=key)
                if not meta:
                    meta = ydl.extract_info(url, download=False)
            else:
                meta = ydl.extract_info(url, download=False)
        except Exception:
            print("Error extracting info:")
            print(traceback.format_exc())
            return ""

    return json.dumps(meta, sort_keys=True, indent=4)
