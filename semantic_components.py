def image(path):
    return f"<img src='{path}' class='ui massive bordered image centered'>"


def link(path, innerText):
    return f"<a href='{path}'>{innerText}</a>"


def button(innerText, home=False):
    if home:
        return f"<button id='home-btn' class='ui button column large'>{innerText}</button>"
    else:
        return f"<button class='ui button large four wide column'>{innerText}</button>"
