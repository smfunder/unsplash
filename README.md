# Unsplash

Unsplash challenge in Kotlin.

## Background

Unsplash is an online resource that provides free high-resolution photos. Unsplash Source allows for simple embedding of Unsplash photos with a URL. In order to render a random image from their collection, you can simply use a URL that specifies the dimensions such as https://source.unsplash.com/800x800.

## Exercise

1) Create a single page native mobile application that loads this webpage in a full screen webview.
2) Disable scrolling for the webview.
3) When the page initially loads, the page has no image. In your native code, fetch a random image URL from https://source.unsplash.com/800x800 and call the javascript function setImageUrl(url) through the webview to update the image rendered by the page.
4) The webpage has a refresh button that makes a call to the javascript function postMessage when pressed. In your native code, you should listen for the refresh event and update the image on the webpage.

