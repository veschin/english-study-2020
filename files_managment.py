import shutil
import os
import glob
import semantic_components as sc

# ----FUNCS


def create_page(num, tag):
    pattern = f"{templates_dir}/Picture{num}.html"
    if not glob.glob(pattern):
        with open(pattern, "w") as new_file, \
                open(f"{templates_dir}/base.html", "r") as base_file:
            base_file_copy = base_file.read()
            new_file.write(
                base_file_copy
                .replace("replace_title", f"Picture{num}")
                .replace(
                    "replace_home_button",
                    f"{sc.button(sc.link('../index.html', 'Back to home'), True)}")
                .replace("replace_body", f"{tag}"))


def create_index_page(templates, buttons):
    with open("index.html", "w") as new_file, \
            open(f"{templates_dir}/base.html", "r") as base_file:
        base_file_copy = base_file.read()
        body = "<div class='ui column grid'><div class='row column'>replace_local</div></div>"
        new_file.write(
            base_file_copy
            .replace("replace_title", "Index page")
            .replace("replace_home_button", "")
            .replace(
                "replace_body", body.replace("replace_local", "\n".join([button for button in buttons]))))


# ----DIRS
images_dir = "images"
templates_dir = "templates"
base_template = "templates/base.html"
images = os.listdir(images_dir)
img_tags = [sc.image(f"../{images_dir}/{image}") for image in images]
# ----


[create_page(num+1, tag) for num, tag in zip(range(len(images)), img_tags)]

templates = os.listdir(templates_dir)[1:]

links = [sc.link(f"{templates_dir}/{template}", template[:-5])
         for template in templates]
buttons = [sc.button(link) for link in links]
create_index_page(templates, buttons)
