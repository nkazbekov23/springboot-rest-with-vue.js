<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Sarafan</v-toolbar-title>
      <v-spacer></v-spacer>
      <span v-if="profile">{{ profile.name }}</span>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon title="выйти">exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-content>
      <v-container v-if="!profile">Необходимо авторизоваться через
        <a href="/login">Google</a>
      </v-container>
      <v-container v-if="profile">
        <messages-list :messages="messages"/>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import MessagesList from 'components/messages/MessagesList.vue'
import {addHandler} from "../util/ws";
export default {
  components: {
    MessagesList
  },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  created() {
    addHandler(data => {
      if (data.objectType === 'MESSAGE') {
        const index = this.messages.findIndex(item => item.id === data.body.id)
        switch (data.eventType) {
          case 'CREATE':
          case 'UPDATE':
            if (index > -1) {
              this.messages.splice(index, 1, data)
            } else {
              this.messages.push(data.body)
            }
            break
          case 'REMOVE':
            this.messages.splice(index, 1)
            default:
                console.error(`Looks like the event type if unknown "${data.eventType}"`)
        }
      } else {
        console.error(`Looks like the object type if unknown "${data.objectType}"`)
      }
    })
  }
}
</script>

<style>

</style>