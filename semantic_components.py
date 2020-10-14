def image(path):
    return f"<img src='{path}' class='ui massive bordered image centered'>"


def link(path, innerText):
    return f"<a href='{path}'>{innerText}</a>"


def button(innerText, home=False):
    if home:
        return f"<button id='home-btn' class='ui button large'>{innerText}</button>"
    else:
        return f"<button class='ui button column large'>{innerText}</button>"


def grid(home_button, image):
    return f"""
    {home_button}
    <div class='ui grid page'>
        <div class=" row">
            <div class="eight wide column">
                {image}
            </div>
            <div class="eight wide column">

            </div>
        </div>
    </div>"""
