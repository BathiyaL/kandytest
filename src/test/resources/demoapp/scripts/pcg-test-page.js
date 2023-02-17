/* Color Box */
let ColorBox =  {
    ColorButtonWindow: {
        // Properties
        colorButtons: document.querySelectorAll('#color-button-container .custom-btn'),

        // Methods
        changeColorCallback: (event) => {
            let backgroundColor = event.target.style.background;
            ColorBox.ColorPanelWindow.changeBackgroundColor(backgroundColor);
        }
    },

    ColorPanelWindow: {
        // Properties
        colorPanel: document.getElementById("color-panel"),

        // Methods
        changeBackgroundColor: (backgroundColor) => {
            ColorBox.ColorPanelWindow.colorPanel.style.background = backgroundColor;
        }
    },
};

let RandomGen = {
    getRandom: (length) => {
        let text = "";
        let possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (let i = 0; i < length; i++)
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
    },

    getRandomWithLeftStatic: (length, staticValue, seperator) => {
        if(!seperator)
            seperator = "";

        return staticValue + seperator + RandomGen.getRandom(length)
    },

    getRandomWithRightStatic: (length, staticValue, seperator) => {
        if(!seperator)
            seperator = "";

        return RandomGen.getRandom(length) + seperator + staticValue
    },

    getRandomWithCenterStatic: (length, staticValue, seperator) => {
        if(!seperator)
            seperator = "";

        return RandomGen.getRandom(length) + seperator + staticValue + seperator + RandomGen.getRandom(length)
    }
};

let Users = {
    getUserElement: (label, id) => {
        // container
        let rowContainer = document.createElement('div');
        rowContainer.classList.add('simple-row');

        // field
        let field = document.createElement('div');
        field.classList.add('simple-field');

        //label
        let fieldLabel = document.createElement('label');
        fieldLabel.classList.add('field-label');
        fieldLabel.innerText = label;
        fieldLabel.for = id;

        // input
        let fieldInput = document.createElement('input');
        fieldInput.type = "text";
        fieldInput.classList.add('field-input');
        fieldInput.id = id;

        // appending elements
        field.appendChild(fieldLabel);
        field.appendChild(fieldInput);

        rowContainer.appendChild(field);

        return rowContainer;
    },

    appendToUsers: () => {
        let usersContainer = document.getElementById("users-container");

        let usersArr = [
            ['User 1 Name', RandomGen.getRandomWithLeftStatic(5, "abc", "_")],
            ['User 2 Name', RandomGen.getRandomWithCenterStatic(5, "abc", "_")],
            ['User 3 Name', RandomGen.getRandomWithRightStatic(5, "abc", "_")]
        ];

        let usersEleArr = [];
        usersArr.forEach((user) => {
            usersEleArr.push(Users.getUserElement(user[0], user[1]))
        });

        usersEleArr.forEach(ele => {
            usersContainer.appendChild(ele);
        });
    }

};

let EmployeeDetailsTabMenu = {
    lastSelectedTab: null,

    tabs: (() => {
        let menus = [];
        let tabContainer = document.getElementById("employee-details-tab");
        let menuContainer = tabContainer.getElementsByClassName("tab-menu")[0];

        for(let i = 0; i < menuContainer.children.length; i++) {
            menus.push(menuContainer.children[i])
        }

        return menus;
    })(),

    selectTabCallback: (event) => {
        console.log();
        let ele = event.currentTarget;

        if(EmployeeDetailsTabMenu.lastSelectedTab) {
            EmployeeDetailsTabMenu.lastSelectedTab.classList.remove("selected-tab");
        }

        EmployeeDetailsTabMenu.lastSelectedTab = ele;
        ele.classList.add("selected-tab");
    }
};

class FocusOut {
    static register(element, callback) {
        FocusOut.eventList.push({
            'element': element,
            'callback': callback
        });
    }

    static unregister(element) {
        let index = FocusOut.eventList.indexOf(element);
        if(~index) FocusOut.eventList.splice(index, 1);
    }

    static start() {
        document.addEventListener('click', (event) => {
            let target = event.target;

            FocusOut.eventList.forEach((listItem) => {
                if(target != listItem.element)
                    listItem.callback();
            });
        });
    }
}

FocusOut.eventList = [];

class Dropdown{
    constructor(id) {
        let self = this;
        this.dropMenuOpened = false;
        this.selectedOption = null;

        this.id = id;
        this.element = document.getElementById(id);

        if(!this.element)
            throw new Error("No dropdown element found with the name");

        // make the root element a dropdown
        this.element.classList.add("custom-select");

        // create title element
        let titleContainer = document.createElement("div");
        this.title = document.createElement("div");

        titleContainer.classList.add("select-title-container");
        this.title.classList.add("select-title");

        // create options
        this.optionContainer = document.createElement("div");
        this.optionContainer.classList.add("select-option-container");

        // attach elements
        titleContainer.appendChild(this.title);

        this.element.appendChild(titleContainer);
        this.element.appendChild(this.optionContainer);

        titleContainer.addEventListener('click', () => {
            if(this.dropMenuOpened)
                this.closeDropMenu();
            else
                this.openDropMenu();
        });

        // default option list closed
        this.closeDropMenu();
    }

    setTitle(title) {
        let textContent = document.createTextNode(title);
        this.title.innerText = title;
    }

    addOption(optionText) {
        let option = this.getOption(optionText);

        // set the event listener
        option.addEventListener('click', (event) => {
            this.onOptionSelect(event, this);
        });

        this.optionContainer.appendChild(option);
    }

    getOption(optionText) {
        let option = document.createElement("div");
        let optionTextContainer = document.createElement("span");
        let optionTextContent = document.createTextNode(optionText);

        // set style
        option.classList.add("select-option");

        optionTextContainer.appendChild(optionTextContent);
        option.appendChild(optionTextContainer);

        return option;
    }

    onOptionSelect(event, self) {
        let ele = event.currentTarget;
        self.selectedOption = ele.innerText;
        self.setTitle("Selected: " + self.selectedOption);
        self.closeDropMenu();
    }

    openDropMenu() {
        this.optionContainer.style.visibility = "visible";
        this.dropMenuOpened = true;
    }

    closeDropMenu() {
        this.optionContainer.style.visibility = "hidden";
        this.dropMenuOpened = false;
    }
}

/*
All custom element actions
*/
let allCEActionClick = document.getElementById("all-custom-element-action-click")
let allCEActionDClick = document.getElementById("all-custom-element-action-dclick")
let allCEActionRClick = document.getElementById("all-custom-element-action-rclick")

allCEActionClick.addEventListener('click', (event) => {
    allCEAMsgBox.clearTimer();
    event.preventDefault();
    allCEAMsgBox.setMessage("Clicked !!!");
});

allCEActionDClick.addEventListener('dblclick', (event) => {
    allCEAMsgBox.clearTimer();
    event.preventDefault();
    allCEAMsgBox.setMessage("Double Clicked !!!");
});

allCEActionRClick.addEventListener('contextmenu', (event) => {
    allCEAMsgBox.clearTimer();
    event.preventDefault();
    allCEAMsgBox.setMessage("Right Clicked !!!");
});

let allCEAMsgBox = document.getElementById("all-custom-element-actions-message-box");

allCEAMsgBox.setMessage = function(message) {
    allCEAMsgBox.innerText = message;
    settimeout(() => {
    
    });
}

allCEAMsgBox.timer = null;

allCEAMsgBox.clearTimer = () => {
    if(allCEAMsgBox.timer)
        clearTimeout(timer);
};

function main() {
    // Color Box
    ColorBox.ColorButtonWindow.colorButtons.forEach((button) => {
        button.addEventListener("click", ColorBox.ColorButtonWindow.changeColorCallback);
    });

    // Game drop down
    let selectGameDropdown = new Dropdown("select-game-container");
    selectGameDropdown.setTitle("Select a game");
    selectGameDropdown.addOption("CS:GO");
    selectGameDropdown.addOption("Call Of Duty");
    selectGameDropdown.addOption("BattleField");
    selectGameDropdown.addOption("PUBG");



    // Dynamic elements
    Users.appendToUsers();

    // Employee Details Tab
    EmployeeDetailsTabMenu.tabs.forEach((ele) => {
        ele.addEventListener('click', EmployeeDetailsTabMenu.selectTabCallback);
    });


    // start focus out event listerners
    FocusOut.start();
}

main();
